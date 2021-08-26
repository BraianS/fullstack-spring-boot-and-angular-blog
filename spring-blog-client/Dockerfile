FROM node:12.22.1-alpine as builder

WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . . 
RUN npm run build --silent

FROM nginx:1.17.1-alpine
VOLUME /var/cache/nginx
COPY --from=builder /usr/src/app/dist/spring-blog-client /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80