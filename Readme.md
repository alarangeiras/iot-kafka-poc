# kafkapoc

## Dependências

* Protocol Buffers Compiler - `sudo apt install protobuf-compiler`

## Gerar classes de stub

Os arquivos **.proto** ficam na pasta src/main/resource/proto. Geralmente os arquivos da aruba não vem com package configurado
O único ajuste a ser feito nos arquivos e garantir a linha abaixo configurada nos mesmos.

> package dev.allanlarangeiras.kafkapoc.proto;

Após isso execute a linha abaixo na raiz do projeto:

> protoc --java_out=./src/main/java ./src/main/resources/proto/streaming.proto  
> protoc --java_out=./src/main/java ./src/main/resources/proto/presence.proto