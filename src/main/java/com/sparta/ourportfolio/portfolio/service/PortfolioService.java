package com.sparta.ourportfolio.portfolio.service;

import com.sparta.ourportfolio.portfolio.dto.PortfolioRequestDto;
import com.sparta.ourportfolio.portfolio.dto.PortfolioResponseDto;
import com.sparta.ourportfolio.portfolio.entity.Portfolio;
import com.sparta.ourportfolio.portfolio.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public ResponseEntity<String> createPortfolio(PortfolioRequestDto portfolioRequestDto) {
        //유저 확인
        Portfolio portfolio = new Portfolio(portfolioRequestDto);
        portfolioRepository.saveAndFlush(portfolio);

        return ResponseEntity.ok().body("포트폴리오 생성 완료");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<PortfolioResponseDto> getPortfolio(Long id) {
        Portfolio portfolio = isExistPortfolio(id);

        PortfolioResponseDto portfolioResponseDto = new PortfolioResponseDto(portfolio);
        return ResponseEntity.ok().body(portfolioResponseDto);
    }

    @Transactional
    public ResponseEntity<String> updatePortfolio(Long id, PortfolioRequestDto portfolioRequestDto) {
        Portfolio portfolio = isExistPortfolio(id);

        //
        portfolio.update(portfolioRequestDto);
        portfolioRepository.save(portfolio);
        return ResponseEntity.ok().body("수정 완료");
    }

    @Transactional
    public ResponseEntity<String> deletePortfolio(Long id) {
        Portfolio portfolio = isExistPortfolio(id);

        portfolioRepository.delete(portfolio);
        return ResponseEntity.ok().body("삭제 완료");
    }

    public Portfolio isExistPortfolio(Long id) {
        return portfolioRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("포트폴리오가 존재하지 않습니다.")
        );
    }
}