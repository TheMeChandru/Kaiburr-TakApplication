# Kaiburr Task Application

---

## **Project Overview**
This project is a **Task Management Application** that allows creating tasks, executing commands in Kubernetes pods, storing task execution history, and visualizing tasks via a React frontend.

---

## **Technologies Used**
- **Backend:** Java 17, Spring Boot, Maven  
- **Database:** MongoDB  
- **Frontend:** React 19 + TypeScript + Ant Design  
- **Containerization:** Docker  
- **Orchestration:** Kubernetes (Minikube/Kind/EKS)  
- **CI/CD:** Jenkins / GitHub Actions  

---

## **How to Run / Deploy**

### **Backend**
```bash
cd taskapp-backend
mvn clean install
mvn spring-boot:run

OR

docker build -t taskapp-backend:1.0 .
docker run -p 8080:8080 taskapp-backend:1.0

cd taskapp-frontend
npm install
npm start

docker build -t taskapp-frontend:1.0 .
docker run -p 3000:80 taskapp-frontend:1.0

kubectl apply -f k8s/mongo-pv.yaml
kubectl apply -f k8s/mongo-deployment.yaml
kubectl apply -f k8s/taskapp-deployment.yaml
kubectl apply -f k8s/taskapp-service.yaml
Frontend Usage

The React app fetches /api/tasks and displays task list with execution history.

Click Run to execute task command and see results.

Screenshots

Requirements: Each screenshot must include current date/time and username.

Backend API working in Postman


Frontend UI showing tasks


Kubernetes pods running

kubectl get pods


MongoDB data persists after pod restart


CI/CD Pipeline successful run
Adding Data

Backend API: Use Postman as shown above.
Frontend: React app automatically fetches /api/tasks and displays newly added tasks.

Commit & Push to GitHub
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main

Cross-Check Before Submission

Backend runs without errors

Frontend fetches and displays data correctly

MongoDB persists data after restarts

Kubernetes deployment works correctly

README.md includes all screenshots with date/time and username

CI/CD pipeline builds successfully
