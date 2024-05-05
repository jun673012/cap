package com.kmou.server.service;

import com.kmou.server.dto.PaymentHoldDTO;
import com.kmou.server.entity.PaymentInfo;
import com.kmou.server.entity.Post;
import com.kmou.server.repository.PaymentRepository;
import com.kmou.server.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentInfoRepository;
    private PostRepository postRepository;

    @Transactional
    public void changeAccepted(String orderId) {
        Optional<PaymentInfo> paymentInfo = paymentInfoRepository.findByOrderId(orderId);
        paymentInfo.ifPresent(info -> {
            Post post = info.getPost();
            post.setPaid(true);
            System.out.println("Post accepted: " + post.isAccepted());
            postRepository.save(post);
        });
    }

    @Transactional
    public void save(PaymentHoldDTO paymentHoldDTO) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(paymentHoldDTO.getOrderId());
        paymentInfo.setName(paymentHoldDTO.getName());

        Post post = postRepository.findById(paymentHoldDTO.getPostId())
               .orElseThrow(() -> new RuntimeException("Post not found with id: " + paymentHoldDTO.getPostId()));
        paymentInfo.setPost(post);

        paymentInfoRepository.save(paymentInfo);
    }
}
