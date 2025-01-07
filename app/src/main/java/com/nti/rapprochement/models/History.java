package com.nti.rapprochement.models;

import com.nti.rapprochement.adapters.RecordAdapter;

import java.util.ArrayList;

public class History {
    final public static History main = new History();

    final private ArrayList<RecordBase> records = new ArrayList<>();

    public void push(RecordBase record) {
        records.add(record);
    }

    public void pop() {
        if (records.isEmpty()) {
            return;
        }
        records.remove(records.size() - 1);
    }

    public ArrayList<RecordBase> getRecords() {
        return records;
    }

    public int getSize() {
        return records.size();
    }
}
