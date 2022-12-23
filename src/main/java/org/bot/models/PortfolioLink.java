package org.bot.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "links")
public class PortfolioLink {
    @Id
    private Long id;
    private String link;

    private String name;
}
