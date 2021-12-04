package com.ngoctta.mapper;

import java.util.List;

import com.ngoctta.model.UserDto;
import com.ngoctta.entity.User;

public interface UserMapper{
	public UserDto EntityToDto(User entity);
	public User DtoToEntity(UserDto dto);
	List<UserDto> EntitiesToDtos(List<User> entities);
}
