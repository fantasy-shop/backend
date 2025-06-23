package net.supercoding.backend.domain.item.service;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateUpdateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemDetailResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemListResponse;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import net.supercoding.backend.domain.item.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final String IMAGE_BASE_PATH = "/home/ubuntu/www/fantasyshop/assets/images/";

    /**
     *  아이템 등록
     */
    @Transactional
    public ItemDetailResponse itemCreate(
            ItemCreateUpdateRequest itemCreateUpdateRequest,
            MultipartFile itemImage
    ) throws IOException {

        ItemEntity newItemEntity = ItemCreateUpdateRequest.toEntity(itemCreateUpdateRequest);

        // 이미지 저장 코드
        if (itemImage != null && !itemImage.isEmpty()) {
            String today = LocalDate.now().toString();

            // 기존 (JAR 환경에서 경로 문제 있음)
            // String projectRoot = System.getProperty("user.dir");
            // String staticPath = projectRoot + "/src/main/resources/static/images/" + today;
            // 변경 (EC2 등 실제 저장 경로로 지정)
//            String staticPath = "/home/ec2-user/images/" + today; // 변경된 부분
            String staticPath = IMAGE_BASE_PATH + today; // 변경된 부분


            File uploadDir = new File(staticPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // 파일 이름 및 확장자 바꾸기
            // 파일의 원래 이름에서 확장자 제거
            String originalName = itemImage.getOriginalFilename();
            String nameWithoutExtension = originalName != null && originalName.contains(".")
                    ? originalName.substring(0, originalName.lastIndexOf('.'))
                    : originalName;

            String savedFileName = UUID.randomUUID() + "_" + nameWithoutExtension + ".jpg";
            File saveFile = new File(uploadDir, savedFileName);

            // Thumbnailator 이미지 처리용 자바 라이브러리 사용
            Thumbnails.of(itemImage.getInputStream())
                    .size(450, 450)         // 비율 유지하면서 둘 중 큰 쪽을 450px로 맞춤
                    .outputFormat("jpg")    // JPEG 형식 저장
                    .outputQuality(0.85)    // 85% 품질 압축
                    .toFile(saveFile);

//            String imageUrl = "/images/" + today + "/" + savedFileName;
            String imageUrl = today + "/" + savedFileName;
            newItemEntity.setItemImageUrl(imageUrl);
        }

        ItemEntity savedItemEntity = itemRepository.save(newItemEntity);

        return ItemDetailResponse.fromEntity(savedItemEntity);
    }

    /**
     *  아이템 목록
     */
    @Transactional(readOnly = true)
    public List<ItemListResponse> itemList(
            String itemCategory,
            String sortCategory,
            String itemNameKeyword
    ) {

        // 필터링 + 키워드 조건에 맞는 리스트 조회 (JPQL 사용)
        List<ItemEntity> itemEntitieList = itemRepository.findByCategoryAndKeyword(itemCategory, itemNameKeyword);

        // 프론트엔드로부터 전달받은 sortCategory로 정렬
        if ("priceDesc".equals(sortCategory)) {
            itemEntitieList.sort(Comparator.comparing(ItemEntity::getItemPrice).reversed());
        } else if ("priceAsc".equals(sortCategory)) {
            itemEntitieList.sort(Comparator.comparing(ItemEntity::getItemPrice));
        } else if ("createdAtDesc".equals(sortCategory)) {
            itemEntitieList.sort(Comparator.comparing(ItemEntity::getItemCreatedAt).reversed());
        } else if ("createdAtAsc".equals(sortCategory)) {
            itemEntitieList.sort(Comparator.comparing(ItemEntity::getItemCreatedAt));
        }

        List<ItemListResponse> itemListResponseList = new ArrayList<>();

        for (ItemEntity itemEntity : itemEntitieList) {
            ItemListResponse itemListResponse = ItemListResponse.fromEntity(itemEntity);

            itemListResponseList.add(itemListResponse);
        }

        return itemListResponseList;
    }

    /**
     *  아이템 삭제
     */
    @Transactional
    public String itemDelete(
            Long itemPk
    ) {
        ItemEntity itemEntity = itemRepository.findById(itemPk)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."));

        if (itemEntity.getItemImageUrl() != null && !itemEntity.getItemImageUrl().isEmpty()) {
//            String projectRoot = System.getProperty("user.dir");
//            String existingImagePath = projectRoot + "/src/main/resources/static" + itemEntity.getItemImageUrl();
//            String existingImagePath = "/home/ec2-user/images" + itemEntity.getItemImageUrl(); // 테스트서버
            String existingImagePath = IMAGE_BASE_PATH + itemEntity.getItemImageUrl();

            File existingImageFile = new File(existingImagePath);
            if (existingImageFile.exists()) {
                existingImageFile.delete(); // 삭제
            }
        }

        itemRepository.delete(itemEntity);
        return "아이템 [" + itemPk + "] 삭제 완료되었습니다.";
    }

    /**
     *  아이템 상세조회
     */
    @Transactional
    public ItemDetailResponse itemDetail(
            Long itemPk
    ) {
        ItemEntity itemEntity = itemRepository.findById(itemPk)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."));

        return ItemDetailResponse.fromEntity(itemEntity);
    }

    /**
     *  아이템 수정
     */
    @Transactional
    public ItemDetailResponse itemUpdate(
            Long itemPk,
            ItemCreateUpdateRequest itemCreateUpdateRequest,
            MultipartFile itemImage
    ) throws IOException {

        ItemEntity itemEntity = itemRepository.findById(itemPk)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."));

        itemEntity.setItemName(itemCreateUpdateRequest.getItemName());
        itemEntity.setItemEffect(itemCreateUpdateRequest.getItemEffect());
        itemEntity.setItemPrice(itemCreateUpdateRequest.getItemPrice());
        itemEntity.setItemDescription(itemCreateUpdateRequest.getItemDescription());
        itemEntity.setItemInventory(itemCreateUpdateRequest.getItemInventory());
        itemEntity.setItemCategory(itemCreateUpdateRequest.getItemCategory());

        // 이미지 수정 코드
        if (itemImage != null && !itemImage.isEmpty()) {

            // 1. 기존 이미지 삭제
            String existingImageUrl = itemEntity.getItemImageUrl(); // 예: "/2025-06-12/abc_image.png"
            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
                // 이전에는 프로젝트 루트 + /src/main/resources/static + 이미지 경로를 사용했음 (JAR 환경에서는 경로가 없음)
                // String projectRoot = System.getProperty("user.dir");
                // String existingImagePath = projectRoot + "/src/main/resources/static" + existingImageUrl;
                // → EC2 등에서 동작하게 하려면 실제 저장 위치를 지정해야 함
//                String existingImagePath = "/home/ec2-user/images" + existingImageUrl; // 변경된 부분
                String existingImagePath = IMAGE_BASE_PATH + existingImageUrl; // 변경된 부분

                File existingImageFile = new File(existingImagePath);
                if (existingImageFile.exists()) {
                    existingImageFile.delete(); // 삭제
                }
            }

            // 2. 새 이미지 저장
            String today = LocalDate.now().toString();

            // 이전에는 프로젝트 루트 + src/main/resources/static/images + 날짜 폴더였음 (JAR 환경에선 존재하지 않음)
            // String projectRoot = System.getProperty("user.dir");
            // String staticPath = projectRoot + "/src/main/resources/static/images/" + today;
            // → 외부 경로로 변경 (EC2 실제 경로)
//            String staticPath = "/home/ec2-user/images/" + today; // 변경된 부분
            String staticPath = IMAGE_BASE_PATH + today; // 변경된 부분

            File uploadDir = new File(staticPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String originalName = itemImage.getOriginalFilename();
            String nameWithoutExtension = originalName != null && originalName.contains(".")
                    ? originalName.substring(0, originalName.lastIndexOf('.'))
                    : originalName;

            String savedFileName = UUID.randomUUID() + "_" + nameWithoutExtension + ".jpg";
            File saveFile = new File(uploadDir, savedFileName);


            Thumbnails.of(itemImage.getInputStream())
                    .size(450, 450)         // 비율 유지하면서 둘 중 큰 쪽을 450px로 맞춤
                    .outputFormat("jpg")    // JPEG 형식 저장
                    .outputQuality(0.85)    // 85% 품질 압축
                    .toFile(saveFile);

//            String imageUrl =  "/images/" + today + "/" + savedFileName;
            String imageUrl =  today + "/" + savedFileName;
            itemEntity.setItemImageUrl(imageUrl);
        }

        return ItemDetailResponse.fromEntity(itemEntity);

    }

/**
 *   @Transactional 어노테이션 ChatGPT 설명
 */
//📌 역할:
//    해당 메서드 또는 클래스의 데이터베이스 트랜잭션 범위를 정의합니다.
//    메서드 수행 중 예외 발생 시 자동으로 롤백 처리를 해줍니다.
//    읽기 전용 트랜잭션이나 트랜잭션 격리 수준 등을 설정할 수 있습니다.
//
//📌 종류:
//    @Transactional (기본적으로 읽기/쓰기 트랜잭션)
//    @Transactional(readOnly = true) (읽기 전용 → 성능 최적화)
//    클래스 수준에 붙이면 클래스 내 모든 메서드에 적용됨
//
//📌 꼭 필요한가요?
//    데이터베이스에 변경이 발생하는 메서드에서는 반드시 필요합니다.
//    예: save, delete, update, setXXX 등의 동작이 포함된 메서드
//    없다면: 트랜잭션이 없으므로 롤백이 안 되고, 중간 상태로 DB에 반영될 수 있어 데이터 무결성이 깨집니다.
//
//    반면, 단순 조회만 하는 메서드에는 @Transactional(readOnly = true)로 지정하면 성능 향상 효과가 있습니다.
}
