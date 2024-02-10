package com.rays.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rays.dao.UserDAOInt;
import com.rays.dto.UserDTO;

@Service
@Transactional
public class UserServiceImpl implements UserServiceInt {

	@Autowired
	public UserDAOInt dao;

	@Autowired
	public AttachmentSeviceInt attachmentServiceInt;

	@Transactional(propagation = Propagation.REQUIRED)
	public long add(UserDTO dto) {
		long pk = dao.add(dto);

		return pk;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(UserDTO dto) {
		UserDTO existDto = findById(dto.getId());
		dto.setImageId(existDto.getImageId());
		dao.update(dto);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(long id) {
		try {
			UserDTO dto = findById(id);
			dao.delete(dto);
			attachmentServiceInt.delete(dto.getImageId());

		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

	}

	@Transactional(readOnly = true)
	public UserDTO findById(long pk) {

		UserDTO dto = dao.findByPk(pk);

		return dto;
	}

	public UserDTO authenticate(String loginId, String password) {

		UserDTO dto = dao.findByUniqueKey("loginId", loginId);

		if (dto != null) {
			if (dto.getPassword().equals(password)) {
				return dto;
			}
		}
		return null;
	}

	public List search(UserDTO dto, int pageNo, int pageSize) {
		List list = dao.search(dto, pageNo, pageSize);

		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public long save(UserDTO dto) {
		Long id = dto.getId();
		if (id != null && id > 0) {
			update(dto);
		} else {
			id = add(dto);
		}
		return id;
	}
	@Transactional(readOnly = true)
	public UserDTO findByLogin(String login) {
		UserDTO dto = dao.findByUniqueKey("loginId", login);
		return dto;
	}


}
