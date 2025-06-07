package hexa.cloud.hexacloud.controller;

import hexa.cloud.hexacloud.model.User;
import hexa.cloud.hexacloud.model.Vps;
import hexa.cloud.hexacloud.model.VpsAccess;
import hexa.cloud.hexacloud.model.VpsAccessId;
import hexa.cloud.hexacloud.dto.request.VpsAccessRequestDTO;
import hexa.cloud.hexacloud.dto.request.response.VpsAccessResponseDTO;
import hexa.cloud.hexacloud.repository.UserRepository;
import hexa.cloud.hexacloud.repository.VpsRepository;
import hexa.cloud.hexacloud.repository.VpsAccessRepository;
import hexa.cloud.hexacloud.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vps-access")
public class VpsAccessController {

    @Autowired
    private VpsAccessRepository vpsAccessRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VpsRepository vpsRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    // Cấp quyền truy cập VPS cho user (chỉ owner hoặc admin)
    @PostMapping("/")
    public VpsAccessResponseDTO grantAccess(@RequestBody VpsAccessRequestDTO dto, @RequestParam Long requesterId) {
        User requester = userRepository.findById(requesterId)
            .orElseThrow(() -> new RuntimeException("Requester not found"));
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Vps vps = vpsRepository.findById(dto.getVpsId())
            .orElseThrow(() -> new RuntimeException("VPS not found"));

        // Kiểm tra requester là owner hoặc admin
        boolean isOwner = vps.getOwnerId() != null && vps.getOwnerId().longValue() == requester.getId();
        boolean isAdmin = userRoleRepository.findByUserId(requester.getId())
            .stream().anyMatch(ur -> "ADMIN".equalsIgnoreCase(ur.getRole().getName()));
        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Only owner or admin can grant access");
        }

        // Không cho phép owner tự cấp quyền cho chính mình
        if (vps.getOwnerId() != null && vps.getOwnerId().longValue() == user.getId()) {
            throw new RuntimeException("Owner already has full access");
        }

        // Không cấp quyền trùng lặp
        VpsAccessId accessId = new VpsAccessId(user.getId().intValue(), vps.getId());
        if (vpsAccessRepository.existsById(accessId)) {
            throw new RuntimeException("User already has access to this VPS");
        }

        VpsAccess access = new VpsAccess(user, vps);
        vpsAccessRepository.save(access);
        return new VpsAccessResponseDTO(user.getId(), vps.getId());
    }

    // Lấy danh sách VPS user được truy cập
    @GetMapping("/user/{userId}")
    public List<VpsAccessResponseDTO> getVpsByUser(@PathVariable Long userId) {
        return vpsAccessRepository.findByUserId(userId).stream()
            .map(a -> new VpsAccessResponseDTO(a.getUser().getId(), a.getVps().getId()))
            .collect(Collectors.toList());
    }

    // Lấy danh sách user được truy cập 1 VPS
    @GetMapping("/vps/{vpsId}")
    public List<VpsAccessResponseDTO> getUsersByVps(@PathVariable Integer vpsId) {
        return vpsAccessRepository.findByVpsId(vpsId).stream()
            .map(a -> new VpsAccessResponseDTO(a.getUser().getId(), a.getVps().getId()))
            .collect(Collectors.toList());
    }

    // Thu hồi quyền truy cập (chỉ owner hoặc admin)
    @DeleteMapping("/")
    public void revokeAccess(@RequestBody VpsAccessRequestDTO dto, @RequestParam Long requesterId) {
        User requester = userRepository.findById(requesterId)
            .orElseThrow(() -> new RuntimeException("Requester not found"));
        Vps vps = vpsRepository.findById(dto.getVpsId())
            .orElseThrow(() -> new RuntimeException("VPS not found"));

        // Kiểm tra requester là owner hoặc admin
        boolean isOwner = vps.getOwnerId() != null && vps.getOwnerId().longValue() == requester.getId();
        boolean isAdmin = userRoleRepository.findByUserId(requester.getId())
            .stream().anyMatch(ur -> "ADMIN".equalsIgnoreCase(ur.getRole().getName()));
        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Only owner or admin can revoke access");
        }

        VpsAccessId id = new VpsAccessId(dto.getUserId().intValue(), dto.getVpsId());
        vpsAccessRepository.deleteById(id);
    }
}