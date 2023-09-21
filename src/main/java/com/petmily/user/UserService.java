package com.petmily.user;

import com.petmily.DataNotFoundException;
import com.petmily.answer.Answer;
import com.petmily.answer.AnswerRepository;
import com.petmily.question.Question;
import com.petmily.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional
    public SiteUser create(String username, String password, String email, String nickname) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname);
        return userRepository.save(user);
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public SiteUser getUser(Long id) {
        Optional<SiteUser> siteUser = this.userRepository.findById(id);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    @Transactional
    public void modify(SiteUser siteUser, String nickname, String email) {
        siteUser.setNickname(nickname);
        siteUser.setEmail(email);
        this.userRepository.save(siteUser);
    }

    @Transactional
    public void passModify(SiteUser siteUser, String password) {
        if (password != null && password.length() > 0) {
            siteUser.setPassword(passwordEncoder.encode(password));
        }
    }

    public void delete(SiteUser siteUser) {
        this.userRepository.delete(siteUser);
    }

    public boolean isCorrectPassword(String username, String password) {
        SiteUser user = getUser(username);
        String actualPassword = user.getPassword();
        return passwordEncoder.matches(password, actualPassword);
    }

    @Transactional
    public void deleteUserAndRelatedData(String username) {
        // 사용자 조회
        SiteUser siteUser = userRepository.findByUsername(username).orElse(null);

        if (siteUser != null) {
            List<Question> userQuestions = questionRepository.findByAuthor(siteUser);

            for (Question question : userQuestions) {
                List<Answer> centerAnswers = question.getAnswerList();
                centerAnswers.clear();
                answerRepository.deleteAll(centerAnswers);
            }

            questionRepository.deleteAll(userQuestions);

            userRepository.delete(siteUser);
        }
    }
}