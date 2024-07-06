from tensorflow.keras import layers, models, optimizers, losses


def create_model(joke_embedding_dim, user_embedding_dim):
    joke_input = layers.Input(shape=(joke_embedding_dim,), name='joke_input')
    user_input = layers.Input(shape=(user_embedding_dim,), name='user_input')

    x = layers.Concatenate()([joke_input, user_input])
    x = layers.Dense(128, activation='relu')(x)
    x = layers.Dense(64, activation='relu')(x)
    output = layers.Dense(1)(x)

    model = models.Model(inputs=[joke_input, user_input], outputs=output)
    return model


# from tensorflow.keras import layers, models, optimizers, losses
# user_embedding_dim = 8
# user_input = layers.Input(shape=(1,), name='user_input')
# user_embedding = layers.Embedding(input_dim=num_users, output_dim=user_embedding_dim, input_length=1)(user_input) # 8
# user_embedding = layers.Flatten()(user_embedding)
#
# joke_input = layers.Input(shape=(combined_df.joke_embedded[1].size,), name='joke_input') # 768,
#
# concatenated = layers.Concatenate()([joke_input, user_embedding])
#
# deep_layer1 = layers.Dense(128, activation='relu')(concatenated)
# deep_layer2 = layers.Dense(64, activation='relu')(concatenated)
# output = layers.Dense(1)(deep_layer1)
#
# model = models.Model(inputs=[joke_input, user_input], outputs=output)
#
# model.compile(optimizer=optimizers.Adam(), loss=losses.MeanSquaredError())


print("HELLO")
a: int = 100
print(a)
