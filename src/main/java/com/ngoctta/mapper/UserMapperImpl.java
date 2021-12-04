package com.ngoctta.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngoctta.model.UserDto;
import com.ngoctta.entity.User;

@Component
public class UserMapperImpl implements UserMapper{

	@Override
	public UserDto EntityToDto(User entity) {
		UserDto userDto = new UserDto();
		userDto.setUser_id(entity.getUser_id());
		userDto.setUsername(entity.getUsername());
		return userDto;
	}

	@Override
	public User DtoToEntity(UserDto dto) {
		User user = new User();
		user.setUser_id(dto.getUser_id());
		user.setUsername(dto.getUsername());
		return user;
	}

	@Override
	public List<UserDto> EntitiesToDtos(List<User> entities) {
		List<UserDto> list = new ArrayList<UserDto>();
		for(User entity : entities) {
			UserDto userDto = new UserDto();
			userDto.setUser_id(entity.getUser_id());
			userDto.setUsername(entity.getUsername());
			list.add(userDto);
		}
		return list;
	}

}
