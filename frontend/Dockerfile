FROM node:22-alpine as build-stage

LABEL author="maires"

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

EXPOSE 5173

ENTRYPOINT [ "npm", "run" ]

CMD [ "dev" ]

