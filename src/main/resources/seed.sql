-- =====================================================
-- SYMPTOM CHECKER — SPRING BOOT SEED DATA
-- =====================================================


-- =====================================================
-- 1. SYMPTOMS
-- =====================================================

INSERT INTO symptoms (name, description, body_part) VALUES
('fever', 'Elevated body temperature above 37.5°C', 'Whole Body'),
('cough', 'Sudden expulsion of air from the lungs', 'Chest'),
('headache', 'Pain in the head or upper neck', 'Head'),
('fatigue', 'Feeling of tiredness or lack of energy', 'Whole Body'),
('sore throat', 'Pain or irritation in the throat', 'Throat'),
('runny nose', 'Excess nasal discharge', 'Nose'),
('sneezing', 'Involuntary expulsion of air through the nose', 'Nose'),
('body ache', 'Pain and discomfort throughout the body', 'Whole Body'),
('chills', 'Feeling of coldness with shivering', 'Whole Body'),
('shortness of breath', 'Difficulty in breathing', 'Chest'),
('chest pain', 'Pain or pressure in the chest area', 'Chest'),
('nausea', 'Feeling of sickness with urge to vomit', 'Stomach'),
('vomiting', 'Forceful expulsion of stomach contents', 'Stomach'),
('diarrhea', 'Loose or watery stools', 'Stomach'),
('stomach pain', 'Pain or cramps in the abdominal area', 'Stomach'),
('loss of appetite', 'Reduced desire to eat', 'Whole Body'),
('joint pain', 'Pain in one or more joints', 'Joints'),
('rash', 'Skin irritation or redness on the body', 'Skin'),
('itching', 'Uncomfortable skin sensation causing urge to scratch', 'Skin'),
('sweating', 'Excessive perspiration', 'Whole Body'),
('dizziness', 'Feeling of lightheadedness or unsteadiness', 'Head'),
('loss of taste', 'Unable to taste food or drinks', 'Mouth'),
('loss of smell', 'Unable to smell anything', 'Nose'),
('muscle weakness', 'Reduced strength in muscles', 'Whole Body'),
('back pain', 'Pain in the lower or upper back area', 'Back'),
('frequent urination', 'Need to urinate more often than usual', 'Urinary'),
('excessive thirst', 'Abnormal feeling of thirst', 'Whole Body'),
('blurred vision', 'Unclear or hazy vision', 'Eyes'),
('swollen lymph nodes', 'Enlarged lymph nodes in neck or armpits', 'Whole Body'),
('yellowing of skin', 'Yellow discoloration of skin or eyes', 'Skin'),
('dark urine', 'Urine that is darker than normal', 'Urinary'),
('pale stool', 'Light colored stool', 'Stomach'),
('anxiety', 'Feeling of worry or unease', 'Mental'),
('depression', 'Persistent feeling of sadness', 'Mental'),
('insomnia', 'Difficulty falling or staying asleep', 'Mental'),
('numbness', 'Loss of sensation in body parts', 'Whole Body'),
('tingling', 'Pins and needles sensation', 'Whole Body'),
('palpitations', 'Rapid or irregular heartbeat sensation', 'Chest'),
('high blood pressure', 'Elevated blood pressure readings', 'Whole Body'),
('weight loss', 'Unintentional loss of body weight', 'Whole Body');


-- =====================================================
-- 2. DISEASES
-- =====================================================

INSERT INTO diseases (name, description, treatment, specialist, severity) VALUES

('Common Cold',
 'Viral infection causing runny nose, sore throat and sneezing.',
 'Rest, fluids, OTC cold medicines.',
 'General Physician',
 'MILD'),

('Influenza (Flu)',
 'Contagious respiratory illness caused by influenza viruses.',
 'Antiviral medication, rest and fluids.',
 'General Physician',
 'MODERATE'),

('COVID-19',
 'Infectious respiratory disease caused by SARS-CoV-2.',
 'Isolation, antivirals, oxygen therapy if needed.',
 'Pulmonologist',
 'SEVERE'),

('Dengue Fever',
 'Mosquito-borne viral infection causing fever and joint pain.',
 'Rest, hydration and paracetamol.',
 'Infectious Disease Specialist',
 'SEVERE'),

('Malaria',
 'Parasitic infection transmitted by mosquito bites.',
 'Antimalarial medications.',
 'Infectious Disease Specialist',
 'SEVERE'),

('Typhoid Fever',
 'Bacterial infection spread through contaminated food or water.',
 'Antibiotics and hydration.',
 'Gastroenterologist',
 'MODERATE'),

('Gastroenteritis',
 'Inflammation of stomach and intestines.',
 'Oral rehydration salts and rest.',
 'Gastroenterologist',
 'MILD'),

('Pneumonia',
 'Lung infection that inflames air sacs.',
 'Antibiotics and oxygen therapy.',
 'Pulmonologist',
 'SEVERE'),

('Tuberculosis (TB)',
 'Serious bacterial infection affecting the lungs.',
 'Long-term antibiotic therapy.',
 'Pulmonologist',
 'SEVERE'),

('Diabetes Mellitus',
 'Chronic disease affecting blood sugar control.',
 'Insulin therapy and lifestyle management.',
 'Endocrinologist',
 'MODERATE'),

('Hypertension',
 'Persistently elevated blood pressure.',
 'Lifestyle changes and medication.',
 'Cardiologist',
 'MODERATE'),

('Jaundice',
 'Yellowing of skin due to high bilirubin.',
 'Treat underlying liver condition.',
 'Hepatologist',
 'MODERATE'),

('Chickenpox',
 'Highly contagious viral infection causing itchy rash.',
 'Antivirals and rest.',
 'Dermatologist',
 'MILD'),

('Migraine',
 'Neurological condition causing severe headaches.',
 'Pain relievers and preventive medications.',
 'Neurologist',
 'MODERATE'),

('Anxiety Disorder',
 'Mental health condition with excessive worry.',
 'Therapy and medication.',
 'Psychiatrist',
 'MODERATE');