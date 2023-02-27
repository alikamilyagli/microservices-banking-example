This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

## Getting started with Development Environment 

First, run the development server:

```bash
npm run dev
# or
yarn dev
```

Then, open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

---
## Getting started with Docker 

### Prerequest
* A running Docker service 

### Build docker image
```bash
docker build -t cenoa-web-app .
```

### Run docker image in a container
```bash
docker run -p 3000:3000 cenoa-web-app
```
Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.