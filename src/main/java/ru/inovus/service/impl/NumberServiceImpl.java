package ru.inovus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.inovus.properties.ApplicationProperties;
import ru.inovus.service.NumberService;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberServiceImpl implements NumberService {

    private final Random random = new Random();
    private final Set<String> issuedNumbers = new HashSet<>();
    private String lastNumber = null;
    private final ApplicationProperties properties;

    @Override
    public String getRandomNumber() {
        String number;
        do {
            number = generateRandomNumber();
        } while (issuedNumbers.contains(number));

        issuedNumbers.add(number);
        return number + properties.getConstant();
    }

    @Override
    public String getNextNumber() {
        String number;
        do {
            number = generateNextNumber();
        } while (issuedNumbers.contains(number));

        issuedNumbers.add(number);
        lastNumber = number;
        return number + properties.getConstant();
    }

    private String generateRandomNumber() {
        String letter1 = getRandomLetter();
        String digit = getRandomDigit();
        String letter2 = getRandomLetter();
        String letter3 = getRandomLetter();
        String number = String.format("%s%s%s%s", letter1, digit, letter2, letter3);
        lastNumber = number;
        return number;
    }

    private String generateNextNumber() {
        if (lastNumber != null) {
            int digits = Integer.parseInt(lastNumber.substring(1, 4));
            String letter1 = lastNumber.substring(0, 1);
            String letter2 = lastNumber.substring(4, 5);
            String letter3 = lastNumber.substring(5, 6);

            if (digits == properties.getMaxDigits()) {
                digits = 0;
                // Увеличение буквы letter3
                int letter3Index = (findLetterIndex(letter3) + 1) % properties.getLetters().length;
                letter3 = properties.getLetters()[letter3Index];

                // Проверка, нужно ли увеличить букву letter2
                if (letter3Index == 0) { // Если letter3 достиг последней буквы, то увеличиваем letter2
                    int letter2Index = (findLetterIndex(letter2) + 1) % properties.getLetters().length;
                    letter2 = properties.getLetters()[letter2Index];

                    // Проверка, нужно ли увеличить букву letter1
                    if (letter2Index == 0) { // Если letter2 достиг последней буквы, то увеличиваем letter1
                        int letter1Index = (findLetterIndex(letter1) + 1) % properties.getLetters().length;
                        letter1 = properties.getLetters()[letter1Index];
                    }
                }
            } else {
                digits++;
            }
            return letter1 + String.format("%03d", digits) + letter2 + letter3;
        }
        return generateRandomNumber();
    }

    private int findLetterIndex(String letter) {
        for (int i = 0; i < properties.getLetters().length; i++) {
            if (properties.getLetters()[i].equals(letter)) {
                return i;
            }
        }
        return -1;
    }

    private String getRandomDigit() {
        return String.valueOf(random.nextInt(properties.getMaxDigits()) + 1);
    }

    private String getRandomLetter() {
        return properties.getLetters()[random.nextInt(properties.getLetters().length)];
    }
}
