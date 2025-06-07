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
    public VpsResponseDTO createVps(@RequestBody VpsRequestDTO dto) {
        Vps vps = new Vps();
        vps.setName(dto.getName());
        vps.setIpAddress(dto.getIpAddress());
        vps.setStatus(dto.getStatus());
        vps.setOs_type(dto.getOsType());
        vps.setOwnerId(dto.getOwnerId());
        Vps saved = vpsRepository.save(vps);
        return toResponseDTO(saved);
    }

    @GetMapping("/")
    public List<VpsResponseDTO> getAllVps() {
        return vpsRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VpsResponseDTO getVpsById(@PathVariable Long id) {
        Vps vps = vpsRepository.findById(id.intValue())
            .orElseThrow(() -> new RuntimeException("VPS not found"));
        return toResponseDTO(vps);
    }

    @PutMapping("/{id}")
    public VpsResponseDTO updateVps(@PathVariable Long id, @RequestBody VpsRequestDTO dto) {
        Vps vps = vpsRepository.findById(id.intValue())
            .orElseThrow(() -> new RuntimeException("VPS not found"));
        vps.setName(dto.getName());
        vps.setIpAddress(dto.getIpAddress());
        vps.setStatus(dto.getStatus());
        vps.setOs_type(dto.getOsType());
        vps.setOwnerId(dto.getOwnerId());
        Vps saved = vpsRepository.save(vps);
        return toResponseDTO(saved);
    }

    @DeleteMapping("/{id}")
    public void deleteVps(@PathVariable Long id) {
        if (!vpsRepository.existsById(id.intValue())) {
            throw new RuntimeException("VPS not found");
        }
        vpsRepository.deleteById(id.intValue());
    }

    private VpsResponseDTO toResponseDTO(Vps vps) {
        return new VpsResponseDTO(
            (long) vps.getId(),
            vps.getName(),
            vps.getIpAddress(),
            vps.getStatus(),
            vps.getOs_type(),
            vps.getCreatedAt() != null ? vps.getCreatedAt().toString() : null,
            vps.getUpdatedAt() != null ? vps.getUpdatedAt().toString() : null,
            null // Nếu muốn trả về user, map thêm ở đây
        );
    }
}