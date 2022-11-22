package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.WithdrawalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWithdrawalRepo extends JpaRepository<WithdrawalOrder, Long> {
}
