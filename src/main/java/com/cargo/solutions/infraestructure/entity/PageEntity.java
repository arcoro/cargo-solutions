package com.cargo.solutions.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@Entity
@Table(name = "pages")
@AllArgsConstructor
@NoArgsConstructor
public class PageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "page_type", nullable = false)
    private String pageType;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "actions", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> actions;

    @Column(name = "previous_menu")
    private Integer previousMenu;

}
