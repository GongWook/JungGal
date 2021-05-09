package com.example.junggar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class QuizpageActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpage);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.quiz_contents);

        Query query = firebaseFirestore.collection("Quiz");

        FirestoreRecyclerOptions<Quiz_class> options = new FirestoreRecyclerOptions.Builder<Quiz_class>()
                .setQuery(query, Quiz_class.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Quiz_class, QuizViewHolder>(options) {
            @NonNull
            @Override
            public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_contents, parent, false);
                return new QuizViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull QuizViewHolder holder, int position, @NonNull Quiz_class model) {
                holder.t_question.setText(model.getQuestion());
                holder.b_answer1.setText(model.getAnswer1());
                holder.b_answer2.setText(model.getAnswer2());
                holder.b_answer3.setText(model.getAnswer3());
                holder.b_answer4.setText(model.getAnswer4());
                holder.b_answer5.setText(model.getAnswer5());

            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);


    }

    private class QuizViewHolder extends  RecyclerView.ViewHolder{

        private TextView t_question;
        private Button b_answer1;
        private Button b_answer2;
        private Button b_answer3;
        private Button b_answer4;
        private Button b_answer5;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            t_question = itemView.findViewById(R.id.text_question);
            b_answer1 = itemView.findViewById(R.id.text_answer1);
            b_answer2 = itemView.findViewById(R.id.text_answer2);
            b_answer3 = itemView.findViewById(R.id.text_answer3);
            b_answer4 = itemView.findViewById(R.id.text_answer4);
            b_answer5 = itemView.findViewById(R.id.text_answer5);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
