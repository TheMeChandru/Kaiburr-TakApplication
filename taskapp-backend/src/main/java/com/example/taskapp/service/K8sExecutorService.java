package com.kaiburr.taskapp.service;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class K8sExecutorService {

    public String executeCommand(String command) {
        try (KubernetesClient client = new DefaultKubernetesClient()) {

            Pod pod = new PodBuilder()
                    .withNewMetadata().withGenerateName("task-pod-").endMetadata()
                    .withNewSpec()
                        .addNewContainer()
                            .withName("task-container")
                            .withImage("ubuntu:latest")
                            .withCommand("bash", "-c", command)
                        .endContainer()
                        .withRestartPolicy("Never")
                    .endSpec()
                    .build();

            pod = client.pods().inNamespace("default").create(pod);

            client.resource(pod).waitUntilCondition(
                    p -> p.getStatus() != null &&
                         "Succeeded".equals(p.getStatus().getPhase()),
                    30,
                    TimeUnit.SECONDS
            );

            Pod finishedPod = client.pods()
                    .inNamespace("default")
                    .withName(pod.getMetadata().getName())
                    .get();

            return finishedPod.getStatus().getPhase();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error executing command: " + e.getMessage();
        }
    }
}
