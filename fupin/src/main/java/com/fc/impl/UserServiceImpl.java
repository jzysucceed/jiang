package com.fc.impl;

import com.fc.dao.UserMapper;
import com.fc.entity.User;
import com.fc.service.UserService;
import com.fc.vo.DataVO;
import com.fc.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultVO getList(Integer pageNum, Integer pageSize, Long id) {
        ResultVO resultVO;

        List<User> users;

        DataVO<User> userDataVO=new DataVO<>();
        if (id!=null){
            users=new ArrayList<>();

            User user=userMapper.selectByPrimaryKey(id);

            resultVO=new ResultVO();

            if (user==null){
                userDataVO.setTotal(0L);
                userDataVO.setList(users);
                userDataVO.setPageNum(pageNum);
                userDataVO.setPageSize(pageSize);

                resultVO.setData(userDataVO);
                resultVO.setSuccess(false );
                resultVO.setCode(404);
                resultVO.setMessage("查无此人");
            }else {
                users.add(user);

                userDataVO.setTotal(1L);
                userDataVO.setList(users);
                userDataVO.setPageNum(pageNum);
                userDataVO.setPageSize(pageSize);

                resultVO.setData(userDataVO);
                resultVO.setSuccess(true);
                resultVO.setCode(200);
                resultVO.setMessage("查询成功");
            }

        }else {
            PageHelper.startPage(pageNum,pageSize);

            users=userMapper.selectByExample(null);

            PageInfo<User> pageInfo=new PageInfo<>(users);

            userDataVO.setTotal(pageInfo.getTotal());
            userDataVO.setList(users);
            userDataVO.setPageNum(pageNum);
            userDataVO.setPageSize(pageSize);

            resultVO=new ResultVO();

            resultVO.setMessage("查询成功");
            resultVO.setSuccess(true);
            resultVO.setCode(200);
            resultVO.setData(userDataVO);
        }

        return resultVO;
    }
}
