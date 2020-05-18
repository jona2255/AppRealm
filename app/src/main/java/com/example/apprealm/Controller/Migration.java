package com.example.apprealm.Controller;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema cancionSchema = schema.get("Cancion");

            cancionSchema
                    .addField("nombreCompleto", String.class, FieldAttribute.REQUIRED)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.set("nombreCompleto", obj.getString("nombre") + " " + obj.getString("artista"));
                        }
                    })
                    .removeField("nombre")
                    .removeField("artista");
            oldVersion++;
        }
    }
}
