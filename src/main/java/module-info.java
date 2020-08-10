open module novel {

    requires jdk.unsupported;
    requires org.slf4j;

    exports novel.api;
    exports novel.api.types.read;
    exports novel.api.types.write;
    exports novel.api.types.write.pens;
    exports novel.api.types.write.writers;
    exports novel.api.types.factory;
    exports novel.api.types.adapt;
    exports novel.api.types.token;
    exports novel.api.types.annotations;
    exports novel.api.streams;

}