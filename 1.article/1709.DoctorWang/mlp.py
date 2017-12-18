import tensorflow as tf
import numpy as np

# Import data
from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("/tmp/data/", one_hot = True)

# Variables
x = tf.placeholder("float", [None, 784])
y = tf.placeholder("float", [None, 10])

w_h = tf.Variable(tf.random_normal([784, 100], mean = 0.0, stddev = 0.05))
b_h = tf.Variable(tf.zeros([100]))
w_o = tf.Variable(tf.random_normal([100, 10], mean = 0.0, stddev = 0.05))
b_o = tf.Variable(tf.zeros([10]))

# Create the model
def model(X, w_h, b_h, w_o, b_o):
    h = tf.nn.relu(tf.matmul(X, w_h) + b_h)
    pred = tf.nn.softmax(tf.matmul(h, w_o) + b_o)
    return pred

y_pred = model(x, w_h, b_h, w_o, b_o)

# Cost Function basic term
cross_entropy = -tf.reduce_sum(y * tf.log(y_pred))

# Regularization terms (weight decay)
L2_loss = tf.nn.l2_loss(w_h) + tf.nn.l2_loss(w_o) + tf.nn.l2_loss(b_h) + tf.nn.l2_loss(b_o)
lambda_2 = 0.01

# the loss and accuracy
loss = cross_entropy + lambda_2 * L2_loss

train_step = tf.train.GradientDescentOptimizer(0.001).minimize(loss)
correct_prediction = tf.equal(tf.argmax(y_pred, 1), tf.argmax(y, 1))
# Train
init = tf.global_variables_initializer()

with tf.Session() as sess:
    sess.run(init)
    print 'Training...'
    for i in range(3001):
        batch_x, batch_y = mnist.train.next_batch(100)
        train_step.run({x: batch_x, y: batch_y})
        if i % 400 == 0:
            train_accuracy = correct_prediction.eval({x: batch_x, y: batch_y})
            print '  step, accurary = %6d: %6.3f' % (i, np.mean(train_accuracy))

            # Test trained model
            print 'accuracy = ', np.mean(correct_prediction.eval({x: mnist.test.images, y: mnist.test.labels}))
                                                                                            
