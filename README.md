# Kaiburr-TakApplication
Project Overview

What the project does.

What technologies were used.

How to Run / Deploy

Backend: mvn spring-boot:run or Docker run commands.

Frontend: npm install → npm start or Docker run commands.

Kubernetes: kubectl apply -f k8s/ commands.

API Usage / UI

List available API endpoints.

Example requests (POSTMAN) with screenshots.

Example frontend usage screenshots.

Screenshots

Input/output for API calls.

UI screens.

Kubernetes pods running.

Docker containers running.

Include date/time and your name in each screenshot.

Step 3: Screenshots

Requirements:

Include current date/time and your name.

Show:

Backend API endpoints working in Postman.

Frontend UI showing task data.

Kubernetes pods running (kubectl get pods).

MongoDB data persists even after pod restart.

CI/CD pipeline runs successfully.

Tip: Use Print Screen or snipping tool on your machine, or add your username in terminal prompts for verification.

Step 4: Adding Data

Backend API: Use Postman to add data:

PUT http://localhost:8080/api/tasks

Body (JSON):

{
  "name": "Test Task",
  "owner": "Chandra Shekar",
  "command": "echo Hello World"
}


Frontend: React app should call /api/tasks and show the newly added tasks.

Step 5: Commit & Push to GitHub

git init

git add .

git commit -m "Initial commit"

git branch -M main

git remote add origin <your-repo-url>

git push -u origin main
Cross-Check Before Submission

Backend runs without errors.

Frontend fetches data correctly.

MongoDB persists data.

Kubernetes deployment works.

README.md includes screenshots and clear instructions.

CI/CD pipeline is included and builds successfully.
