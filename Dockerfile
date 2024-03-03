FROM ghcr.io/graalvm/native-image-community:21 as build
WORKDIR /app
COPY . /app
RUN ./mvnw -Pnative native:compile

FROM alpine
COPY --from=build /app/target/rinha-de-backend-2024-q1 /api
RUN apk add libc6-compat
EXPOSE 8080
CMD ["./api"]
