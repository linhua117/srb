package com.lh.srb.core.service.impl;

import com.lh.srb.core.entity.UserAccount;
import com.lh.srb.core.mapper.UserAccountMapper;
import com.lh.srb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author linhua
 * @since 2021-09-01
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
