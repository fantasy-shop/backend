package net.supercoding.backend.domain.item.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateRequest;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemCreateResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemDetailResponse;
import net.supercoding.backend.domain.item.dto.ItemDto.ItemListResponse;
import net.supercoding.backend.domain.item.entity.ItemEntity;
import net.supercoding.backend.domain.item.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemCreateResponse itemCreate(ItemCreateRequest itemCreateRequest, MultipartFile image)
            throws IOException {

        ItemEntity newItemEntity = ItemCreateRequest.toEntity(itemCreateRequest);

        if (image != null && !image.isEmpty()) {
            String today = LocalDate.now().toString();

            // src/main/resources/static 경로를 절대 경로로 지정
            String projectRoot = System.getProperty("user.dir"); // 현재 프로젝트 루트
            String staticPath = projectRoot + "/src/main/resources/static/" + today;

            File uploadDir = new File(staticPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String savedFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            File saveFile = new File(uploadDir, savedFileName);
            image.transferTo(saveFile);

            // 프론트에서 접근 가능한 URL 경로
            String imageUrl = "/" + today + "/" + savedFileName;
            newItemEntity.setItemImageUrl(imageUrl);
        }

        ItemEntity savedItemEntity = itemRepository.save(newItemEntity);

        return ItemCreateResponse.fromEntity(savedItemEntity);

//        위 코드를 한줄로하면
//        return ItemCreateResponse.fromEntity(
//                itemRepository.save(
//                        ItemCreateRequest.toEntity(itemCreateRequest)
//                )
//        );
    }

    @Transactional(readOnly = true)
    public List<ItemListResponse> itemList(
            String itemCategory,
            String sortCategory
    ) {

        System.out.println("itemCategory: " + itemCategory);
        System.out.println("sortCategory: " + sortCategory);


        List<ItemEntity> itemEntitieList;

        // 프론트엔드로부터 전달받은 itemCategory로 필터
        if (Objects.equals(itemCategory, "all")) {
            itemEntitieList = itemRepository.findAll();
        } else {
            itemEntitieList = itemRepository.findByItemCategory(itemCategory);
        }

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

    @Transactional
    public String itemDelete(Long itemPk) {
        ItemEntity itemEntity = itemRepository.findById(itemPk)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."));

        itemRepository.delete(itemEntity);
        return "아이템 [" + itemPk + "] 삭제 완료되었습니다.";
    }

    @Transactional
    public ItemDetailResponse itemDetail(Long itemPk) {
        ItemEntity itemEntity = itemRepository.findById(itemPk)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "아이템을 찾을 수 없습니다."));

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
