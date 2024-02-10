package com.rays.service;

import java.util.List;

import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

public interface RoleServiceInt  {
	
	public long add(UserDTO dto);

	public void update(UserDTO dto);
	
	public void delete (long id );
	
	
	public List search(RoleDTO dto, int pageNo, int pageSize);
	
	
	
	
}
