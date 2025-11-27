# Naive-Bayes-classifier-Java
Naive Bayes text classifier in Java using bag-of-words features, trained and evaluated with confusion matrices on labelled documents.

This project implements a Naive Bayes classifier for text classification in Java. Documents are converted into bag-of-words feature vectors, and the model estimates class priors and class-conditional word probabilities from labelled training data under the standard conditional-independence assumption. New documents are scored using log-probabilities, and the classifier is evaluated on a held-out test set using accuracy and confusion matrices to inspect typical misclassifications.
