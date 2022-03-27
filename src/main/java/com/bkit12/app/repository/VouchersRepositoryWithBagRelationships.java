package com.bkit12.app.repository;

import com.bkit12.app.domain.Vouchers;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface VouchersRepositoryWithBagRelationships {
    Optional<Vouchers> fetchBagRelationships(Optional<Vouchers> vouchers);

    List<Vouchers> fetchBagRelationships(List<Vouchers> vouchers);

    Page<Vouchers> fetchBagRelationships(Page<Vouchers> vouchers);
}
