package net.lab1024.sa.admin.module.business.oa.notice.service;

import cn.hutool.core.util.StrUtil;
import net.lab1024.sa.admin.module.business.oa.notice.dao.NoticeTypeDao;
import net.lab1024.sa.admin.module.business.oa.notice.domain.entity.NoticeTypeEntity;
import net.lab1024.sa.admin.module.business.oa.notice.domain.vo.NoticeTypeVO;
import net.lab1024.sa.common.common.domain.ResponseDTO;
import net.lab1024.sa.common.common.util.SmartBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NoticeTypeService {

    @Autowired
    private NoticeTypeDao noticeTypeDao;

    /**
     * 查询全部
     * @return
     */
    public List<NoticeTypeVO> getAll() {
        return SmartBeanUtil.copyList(noticeTypeDao.selectList(null), NoticeTypeVO.class);
    }

    public NoticeTypeVO getByNoticeTypeId(Long noticceTypeId) {
        return SmartBeanUtil.copy(noticeTypeDao.selectById(noticceTypeId), NoticeTypeVO.class);
    }

    public synchronized ResponseDTO<String> add(String name) {
        if (StrUtil.isBlank(name)) {
            return ResponseDTO.userErrorParam("유형 이름은 비워둘 수 없습니다.");
        }

        List<NoticeTypeEntity> noticeTypeEntityList = noticeTypeDao.selectList(null);
        if (!CollectionUtils.isEmpty(noticeTypeEntityList)) {
            boolean exist = noticeTypeEntityList.stream().map(NoticeTypeEntity::getNoticeTypeName).collect(Collectors.toSet()).contains(name);
            if (exist) {
                return ResponseDTO.userErrorParam("유형 이름이 이미 존재합니다.");
            }
        }
        noticeTypeDao.insert(NoticeTypeEntity.builder().noticeTypeName(name).build());
        return ResponseDTO.ok();
    }

    public synchronized ResponseDTO<String> update(Long noticeTypeId, String name) {
        if (StrUtil.isBlank(name)) {
            return ResponseDTO.userErrorParam("유형 이름은 비워둘 수 없습니다.");
        }

        NoticeTypeEntity noticeTypeEntity = noticeTypeDao.selectById(noticeTypeId);
        if (noticeTypeEntity == null) {
            return ResponseDTO.userErrorParam("유형 이름이 존재하지 않습니다.");
        }

        List<NoticeTypeEntity> noticeTypeEntityList = noticeTypeDao.selectList(null);
        if (!CollectionUtils.isEmpty(noticeTypeEntityList)) {
            Optional<NoticeTypeEntity> optionalNoticeTypeEntity = noticeTypeEntityList.stream().filter(e -> e.getNoticeTypeName().equals(name)).findFirst();
            if (optionalNoticeTypeEntity.isPresent() && !optionalNoticeTypeEntity.get().getNoticeTypeId().equals(noticeTypeId)) {
                return ResponseDTO.userErrorParam("유형 이름이 이미 존재합니다.");
            }
        }
        noticeTypeEntity.setNoticeTypeName(name);
        noticeTypeDao.updateById(noticeTypeEntity);
        return ResponseDTO.ok();
    }

    public synchronized ResponseDTO<String> delete(Long noticeTypeId) {
        noticeTypeDao.deleteById(noticeTypeId);
        return ResponseDTO.ok();
    }

}
