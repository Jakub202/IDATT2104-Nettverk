FROM amytabb/docker_ubuntu16_essentials
RUN apt-get update && apt-get install -y curl
WORKDIR /HelloWorld/
RUN curl -o helloworld.cpp https://example.com/helloworld.cpp
RUN g++ -o HelloWorld helloworld.cpp
CMD ["./HelloWorld"]
