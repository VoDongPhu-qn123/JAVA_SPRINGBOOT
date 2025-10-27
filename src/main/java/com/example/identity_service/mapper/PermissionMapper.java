package com.example.identity_service.mapper;

import com.example.identity_service.dto.request.PermissionRequest;
import com.example.identity_service.dto.response.PermissionResponse;
import com.example.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request); //Chuyển từ DTO đầu vào (PermissionRequest) → sang Entity (Permission)
    PermissionResponse toPermissionResponse(Permission permission); //Chuyển ngược lại: từ Entity (Permission) → sang DTO trả ra client (PermissionResponse)
}
