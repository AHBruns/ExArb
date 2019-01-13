package ExArb.Networking;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

public abstract class GenericConnection{
    public abstract JsonReader Execute() throws IOException;
}
