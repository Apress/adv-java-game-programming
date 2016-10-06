     package com.croftsoft.apps.insight;

     import java.applet.Applet;
     import java.awt.Graphics;
     import java.awt.*;
     import java.awt.Color;
     import java.awt.Point;
     import java.awt.Rectangle;

     public class Backprop_Net {
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     double        learning_rate     = 0.1;
     double        momentum_constant = 0.9;
     int           layers; // synaptic weight layers
     boolean       using_bias_inputs;
     Matrix_Array  inputs;
     Matrix_Array  weights;
     Matrix_Array  weighted_sums;
     Matrix_Array  outputs;
     Matrix_Array  local_gradients;
     Matrix_Array  sum_weighted_deltas;
     Matrix_Array  weights_delta;
     Matrix_Array  weights_momentum;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Backprop_Net (
       int      layer0_inputs,
       int [ ]  neurons_per_layer,
       boolean  add_bias_inputs,
       boolean  randomize_initial_weights ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method.
     // The first layer, layer 0, is NOT an input layer.
     // Should return exception if number of layers < 1.
     //////////////////////////////////////////////////////////////////////
       int  bias   = add_bias_inputs ? 1 : 0;
     //////////////////////////////////////////////////////////////////////
       this.layers = neurons_per_layer.length;
       this.using_bias_inputs = add_bias_inputs;
       this.inputs              = new Matrix_Array ( layers );
       this.weights             = new Matrix_Array ( layers );
       this.weighted_sums       = new Matrix_Array ( layers );
       this.outputs             = new Matrix_Array ( layers );
       this.local_gradients     = new Matrix_Array ( layers );
       this.sum_weighted_deltas = new Matrix_Array ( layers );
       this.weights_delta       = new Matrix_Array ( layers );
       this.weights_momentum    = new Matrix_Array ( layers );
       for ( int index_layer = 0;
                 index_layer < layers;
                 index_layer++ ) {
         if ( index_layer == 0 ) {
           inputs.m [ 0 ] = new Matrix ( layer0_inputs + bias, 1 );
           if ( add_bias_inputs ) {
             inputs.m [ 0 ].data [ 0 ] [ 0 ] = 1.0;
           }
           weights.m [ 0 ] = new Matrix (
             layer0_inputs + bias, neurons_per_layer [ 0 ] );
           weighted_sums.m [ 0 ]
             = new Matrix ( neurons_per_layer [ 0 ], 1 );
           outputs.m [ 0 ]
             = new Matrix ( neurons_per_layer [ 0 ], 1 );
           local_gradients.m [ 0 ]
             = new Matrix ( neurons_per_layer [ 0 ], 1 );
           sum_weighted_deltas.m [ 0 ]
             = new Matrix ( neurons_per_layer [ 0 ], 1 );
           weights_delta.m [ 0 ] = new Matrix ( weights.m [ 0 ] );
           weights_momentum.m [ 0 ] = new Matrix ( weights.m [ 0 ] );
         } else {
           inputs.m [ index_layer ] = new Matrix (
             neurons_per_layer [ index_layer - 1 ] + bias, 1 );
           if ( add_bias_inputs ) {
             inputs.m [ index_layer ].data [ 0 ] [ 0 ] = 1.0;
           }
           weights.m [ index_layer ] = new Matrix (
             neurons_per_layer [ index_layer - 1 ] + bias,
             neurons_per_layer [ index_layer ] );
           weighted_sums.m [ index_layer ] = new Matrix (
             neurons_per_layer [ index_layer ], 1 );
           outputs.m [ index_layer ] = new Matrix (
             neurons_per_layer [ index_layer ], 1 );
           local_gradients.m [ index_layer ] = new Matrix (
             neurons_per_layer [ index_layer ], 1 );
           sum_weighted_deltas.m [ index_layer ] = new Matrix (
             neurons_per_layer [ index_layer ], 1 );
           weights_delta.m [ index_layer ]
             = new Matrix ( weights.m [ index_layer ] );
           weights_momentum.m [ index_layer ]
             = new Matrix ( weights.m [ index_layer ] );
         }
       }
       if ( randomize_initial_weights ) {
         weights_randomize_uniform ( -1.0, 1.0 );
       }
     }
       
     public Matrix_Array  update_forward_backward (
       Matrix  input_vector,
       Matrix  outputs_desired ) {
     //////////////////////////////////////////////////////////////////////
       weights_update ( );
       Matrix  outputs_actual = forward_pass ( input_vector );
       Matrix  error_vector = backward_pass ( outputs_desired );
       Matrix [ ]  matrices_array = new Matrix [ 2 ];
       matrices_array [ 0 ] = outputs_actual;
       matrices_array [ 1 ] = error_vector;
       Matrix_Array  results = new Matrix_Array ( matrices_array );
       return results;
     }

     public Matrix  forward_pass ( Matrix  input_vector ) {
     //////////////////////////////////////////////////////////////////////
     // needs to raise exception if input_vector and inputs [ 0 ] bad size
     // Returns the output vector.
     //////////////////////////////////////////////////////////////////////
       for ( int index_layer = 0; index_layer < layers; index_layer++ ) {
         if ( using_bias_inputs ) {
           for ( int index_row = 1;
                     index_row < inputs.m [ index_layer ].rows;
                     index_row++ ) {
             if ( index_layer == 0 ) {
               inputs.m [ index_layer ].data [ index_row ] [ 0 ]
                 = input_vector.data [ index_row - 1 ] [ 0 ];
             } else {
               inputs.m [ index_layer ].data [ index_row ] [ 0 ]
                 = outputs.m [ index_layer - 1 ].data [ index_row - 1 ] [ 0 ];
             }
           }
         } else {
           if ( index_layer == 0 ) {
             inputs.m [ index_layer ] = new Matrix ( input_vector );
           } else {
             inputs.m [ index_layer ]
               = new Matrix ( outputs.m [ index_layer ] );
           }
         }
         weighted_sums.m [ index_layer ] = Matrix.multiply (
           weights.m [ index_layer ].transpose ( ),
           inputs.m [ index_layer ] );
         outputs.m [ index_layer ]
           = weighted_sums.m [ index_layer ].sigmoid ( );
       }
       return new Matrix ( outputs.m [ layers - 1 ] );
     }

     public Matrix  backward_pass ( Matrix  desired_outputs ) {
     //////////////////////////////////////////////////////////////////////
     // raise exception if desired_outputs wrong size
     // Returns error vector.
     //////////////////////////////////////////////////////////////////////
       Matrix  error_vector
         = desired_outputs.subtract ( outputs.m [ layers - 1 ] );
// check this again, get rid of sigmoid_derivative
       local_gradients.m [ layers - 1 ]
         = weighted_sums.m [ layers - 1 ].sigmoid_derivative ( );
       local_gradients.m [ layers - 1 ] = Matrix.multiply_elements (
         local_gradients.m [ layers - 1 ], error_vector );
       weights_delta.m [ layers - 1 ]
         = Matrix.multiply ( inputs.m [ layers - 1 ],
         local_gradients.m [ layers - 1 ].transpose ( ) );
// needs transpose or sum below?
       for ( int index_layer = layers - 2;
                 index_layer >= 0;
                 index_layer-- ) {
         // Chopping off the weights from the bias neuron.
         sum_weighted_deltas.m [ index_layer ]
           = weights.m [ index_layer + 1 ].sub_matrix (
           1, weights.m [ index_layer + 1 ].rows    - 1,
           0, weights.m [ index_layer + 1 ].columns - 1 );
         // sum_weighted_deltas goes from matrix to vector here
// Could be big fat error here...
         sum_weighted_deltas.m [ index_layer ] = Matrix.multiply (
           sum_weighted_deltas.m [ index_layer ],
           local_gradients.m [ index_layer + 1 ] );
         local_gradients.m [ index_layer ]
           = outputs.m [ index_layer ].subtract (
             outputs.m [ index_layer ].square_elements ( ) );
         local_gradients.m [ index_layer ] = Matrix.multiply_elements (
           local_gradients.m [ index_layer ],
           sum_weighted_deltas.m [ index_layer ] );
         weights_delta.m [ index_layer ] = Matrix.multiply (
           inputs.m [ index_layer ],
           local_gradients.m [ index_layer ].transpose ( ) );
       }
       weights_delta = weights_delta.multiply ( learning_rate );
       weights_delta = weights_delta.add ( weights_momentum );
       weights_momentum = weights_delta.multiply ( momentum_constant );
       return error_vector;
     }

     public void  weights_update ( ) {
     //////////////////////////////////////////////////////////////////////
       weights = weights.add ( weights_delta );
     }

     public void  weights_randomize_uniform ( double  min, double  max ) {
     //////////////////////////////////////////////////////////////////////
       for ( int index_layer = 0; index_layer < layers; index_layer++ ) {
         weights.m [ index_layer ]
           = weights.m [ index_layer ].randomize_uniform ( min, max );
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
