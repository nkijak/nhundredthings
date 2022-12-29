package com.kinnack.nthings.model.colyseus;

import io.colyseus.annotations.SchemaField;
import io.colyseus.serializer.schema.Schema;
import io.colyseus.serializer.schema.types.MapSchema;

/*
export class MyRoomState extends Schema {
  @type("string") mySynchronizedProperty: string = "Hello world";
  @type("number") numParticipants: number = 0;

  @type({ map: UserState }) users = new MapSchema<UserState>();

  addUser(sessionId: string) {
    this.users.set(sessionId, new UserState());
    this.numParticipants += 1;
  }

  removeUser(sessionId: string) {
    this.users.delete(sessionId)
  }

  onUserRep(sessionId: string) {
    let userState = this.users.get(sessionId);
    console.log("#onUserRep", { userState });
    userState.reps += 1;
  }
}

 */

public class MyRoomState extends Schema {
    @SchemaField(type="0/string")
    public String mySynchronizedProperty = "Hello world";

    @SchemaField(type="1/number")
    public int numParticipants = 0;

    @SchemaField(type = "2/map/ref", ref = UserState.class)
    public MapSchema<UserState> cells = new MapSchema<>(UserState.class);
}
