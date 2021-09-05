package com.lh.srb.core.service.impl;

import com.lh.srb.core.entity.UserInfo;
import com.lh.srb.core.mapper.UserInfoMapper;
import com.lh.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author linhua
 * @since 2021-09-01
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
