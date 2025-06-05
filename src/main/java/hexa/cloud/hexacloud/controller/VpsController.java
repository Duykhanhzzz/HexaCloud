package hexa.cloud.hexacloud.controller;

import hexa.cloud.hexacloud.model.Vps;
import hexa.cloud.hexacloud.dto.request.VpsRequestDTO;
import hexa.cloud.hexacloud.dto.request.response.VpsResponseDTO;
import hexa.cloud.hexacloud.repository.VpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vps")
public class VpsController {

    @Autowired
    private VpsRepository vpsRepository;

    @PostMapping("/")
    public VpsResponseDTO createVps(@RequestBody VpsRequestDTO vpsRequestDTO) {
        Vps vps = new Vps();
        vps.setName(vpsRequestDTO.getVpsName());
        vps.setStatus("active");
        // Set các trường khác nếu có
        Vps saved = vpsRepository.save(vps);
        return new VpsResponseDTO(
            (long) saved.getId(),
            saved.getName(),
            saved.getIpAddress(),
            saved.getStatus(),
            saved.getOs_type(),
            saved.getCreatedAt() != null ? saved.getCreatedAt().toString() : null,
            saved.getUpdatedAt() != null ? saved.getUpdatedAt().toString() : null,
            null // user nếu có
        );
    }

    @GetMapping("/")
    public List<VpsResponseDTO> getAllVps() {
        return vpsRepository.findAll().stream().map(vps -> new VpsResponseDTO(
            (long) vps.getId(),
            vps.getName(),
            vps.getIpAddress(),
            vps.getStatus(),
            vps.getOs_type(),
            vps.getCreatedAt() != null ? vps.getCreatedAt().toString() : null,
            vps.getUpdatedAt() != null ? vps.getUpdatedAt().toString() : null,
            null
        )).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VpsResponseDTO getVpsById(@PathVariable Long id) {
        Vps vps = vpsRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("VPS not found"));
        return new VpsResponseDTO(
            Long.valueOf(vps.getId()),
            vps.getName(),
            vps.getIpAddress(),
            vps.getStatus(),
            vps.getOs_type(),
            vps.getCreatedAt() != null ? vps.getCreatedAt().toString() : null,
            vps.getUpdatedAt() != null ? vps.getUpdatedAt().toString() : null,
            null
        );
    }
}