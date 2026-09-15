package com.impacto.application.service;
import com.impacto.infrastructure.persistence.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class ResourceCodeService {
    private final ResourceRepository repo;
    private final AtomicLong counter=new AtomicLong();
    public ResourceCodeService(ResourceRepository r) {
        repo=r;
    }
    @Transactional
    public synchronized String next() {
        long base=counter.incrementAndGet()+repo.count();
        return "RS-"+String.format("%06d",base);
    }
}
