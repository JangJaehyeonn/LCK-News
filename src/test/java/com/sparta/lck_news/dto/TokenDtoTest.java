package com.sparta.lck_news.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenDtoTest {

    @Test
    public void testTokenDtoConstructorAndGetters() {
        // Given
        String grantType = "Bearer";
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";

        // When
        TokenDto tokenDto = new TokenDto(grantType, accessToken, refreshToken);

        // Then
        assertNotNull(tokenDto);
        assertEquals(grantType, tokenDto.getGrantType());
        assertEquals(accessToken, tokenDto.getAccessToken());
        assertEquals(refreshToken, tokenDto.getRefreshToken());
    }

    @Test
    public void testBuilderPattern() {
        // Given
        String grantType = "Bearer";
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";

        // When
        TokenDto tokenDto = TokenDto.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        // Then
        assertNotNull(tokenDto);
        assertEquals(grantType, tokenDto.getGrantType());
        assertEquals(accessToken, tokenDto.getAccessToken());
        assertEquals(refreshToken, tokenDto.getRefreshToken());
    }

    @Test
    public void testToStringMethod() {
        // Given
        String grantType = "Bearer";
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";
        TokenDto tokenDto = new TokenDto(grantType, accessToken, refreshToken);

        // When
        String toStringResult = tokenDto.toString();

        // Then
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("grantType=" + grantType));
        assertTrue(toStringResult.contains("accessToken=" + accessToken));
        assertTrue(toStringResult.contains("refreshToken=" + refreshToken));
    }

    @Test
    public void testEqualsAndHashCodeMethods() {
        // Given
        String grantType = "Bearer";
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";
        TokenDto tokenDto1 = new TokenDto(grantType, accessToken, refreshToken);
        TokenDto tokenDto2 = new TokenDto(grantType, accessToken, refreshToken);

        // When
        boolean equalsResult = tokenDto1.equals(tokenDto2);
        int hashCode1 = tokenDto1.hashCode();
        int hashCode2 = tokenDto2.hashCode();

        // Then
        assertTrue(equalsResult);
        assertEquals(hashCode1, hashCode2);
    }
}
