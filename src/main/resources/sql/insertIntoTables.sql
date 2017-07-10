INSERT INTO DOCTORS (id, name, surname, second_name, specialization)
 VALUES (NEXT VALUE FOR doctorSequence,'Иван', 'Иванов', 'Петрович', 'Кардиолог' );

INSERT INTO DOCTORS (id, name, surname, second_name, specialization)
 VALUES (NEXT VALUE FOR doctorSequence,'Семен', 'Петров', 'Иванович', 'Окулист' );

INSERT INTO DOCTORS (id, name, surname, second_name, specialization)
 VALUES (NEXT VALUE FOR doctorSequence,'Николай', 'Сидоров', 'Семенович', 'Ортопед' );


INSERT INTO PATIENTS(id, name, surname, second_name, phone_number)
 VALUES (NEXT VALUE FOR patientSequence, 'Дмитрий','Козлов','Артемович', 89277394995);

INSERT INTO PATIENTS(id, name, surname, second_name, phone_number)
 VALUES (NEXT VALUE FOR patientSequence, 'Василий','Еленев','Дмитриевич', 89277336971);

INSERT INTO PATIENTS(id, name, surname, second_name, phone_number)
 VALUES (NEXT VALUE FOR patientSequence, 'Юрий','Резник','Данилович', 89277262123);


INSERT INTO RECIPES (id, description, patient_id, doctor_id, date_of_create, validity, priority)
 VALUES (NEXT VALUE FOR recipeSequence,'Принимать неболин 2 раза в день', 1,2,to_date('12/12/2016', 'DD/MM/YYYY'), 30, 'Нормальный');

INSERT INTO RECIPES (id, description, patient_id, doctor_id, date_of_create, validity, priority)
 VALUES (NEXT VALUE FOR recipeSequence,'2 раза пройти курс успокоина', 2,3,to_date('24/04/2017', 'DD/MM/YYYY'), 60, 'Срочный');

INSERT INTO RECIPES (id, description, patient_id, doctor_id, date_of_create, validity, priority)
 VALUES (NEXT VALUE FOR recipeSequence,'Вкалывать 3 раза в день выздоровин', 3,1,to_date('07/05/2017', 'DD/MM/YYYY'), 15, 'Немедленный');