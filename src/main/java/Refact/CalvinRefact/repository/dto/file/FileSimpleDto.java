package Refact.CalvinRefact.repository.dto.file;

import lombok.Data;

@Data
public class FileSimpleDto {
    private Long id;
    private String original_name;
    private String save_name;
    private int size;

    public FileSimpleDto(Long id, String original_name, String save_name, int size) {
        this.id = id;
        this.original_name = original_name;
        this.save_name = save_name;
        this.size = size;
    }

    public FileSimpleDto(String save_name) {
        this.save_name = save_name;
    }

    public FileSimpleDto() {
    }
}
