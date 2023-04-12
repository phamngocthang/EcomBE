package com.ecomerce.android.responsitory;

import com.ecomerce.android.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

}
