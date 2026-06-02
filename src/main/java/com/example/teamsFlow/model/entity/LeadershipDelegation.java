package com.example.teamsFlow.model.entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadershipDelegation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long leaderId;
    private Long delegatedToId;
    private String startDate;
    private String endDate;

    public boolean isActive(String currentDate) {
        return currentDate.compareTo(startDate) >= 0
            && currentDate.compareTo(endDate) <= 0;
    }
}