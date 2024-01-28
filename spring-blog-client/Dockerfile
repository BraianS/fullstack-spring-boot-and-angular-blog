FROM node:20-alpine as builder

WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . . 
RUN npm run build --silent

FROM nginx:alpine
VOLUME /var/cache/nginx
COPY --from=builder /usr/src/app/dist/spring-blog-client /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80