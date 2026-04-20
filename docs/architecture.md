# CareerLens Prototype Architecture

- Frontend (`/frontend`): Next.js dashboard pages for job seeker, recruiter, admin.
- Backend (`/backend`): Spring Boot project with layered architecture.
- Seed (`/seed-data`): JSON seed files loaded by backend startup loader.

## Layering
Controller → Service → Repository → DB(JPA Entity) → Response DTO

## Core Flows
1) Recommendation diagnosis → 2) Planner roadmap → 3) Application Kanban → 4) Settlement checklist.
5) Verification profile is independent and can be viewed by recruiters/admin.


## External collection policy
- No ATS/Greenhouse/scraping sync jobs.
- Job data is managed manually or via seed files only.
