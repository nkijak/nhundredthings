package com.kinnack.nthings.model.colyseus;

import io.colyseus.annotations.SchemaField;
import io.colyseus.serializer.schema.Schema;

/*
import { Schema, MapSchema, Context, type } from "@colyseus/schema";

        export class UserState extends Schema {
    @type("number") reps: number = 0;
}

}*/


public class UserState extends Schema {
    @SchemaField(type="0/int64")
    public int reps = 0;
}
