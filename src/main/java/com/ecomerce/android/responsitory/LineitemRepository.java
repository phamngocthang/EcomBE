package com.ecomerce.android.responsitory;

import com.ecomerce.android.model.Lineitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineitemRepository extends JpaRepository<Lineitem, Integer> {
}
