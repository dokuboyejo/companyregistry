-- Table: public.company

-- DROP TABLE public.company;

CREATE TABLE public.company
(
  id bigint NOT NULL DEFAULT nextval('company_id_seq'::regclass),
  name text NOT NULL,
  address text NOT NULL,
  city text NOT NULL,
  country text NOT NULL,
  email character varying,
  "phoneNumber" character varying,
  "beneficiaryId" bigint NOT NULL,
  CONSTRAINT pk_company_id PRIMARY KEY (id, "beneficiaryId"),
  CONSTRAINT fk_beneficiary_id FOREIGN KEY ("beneficiaryId")
      REFERENCES public.beneficiary (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.company
  OWNER TO postgres;

-- Index: public.ix_company_id

-- DROP INDEX public.ix_company_id;

CREATE INDEX ix_company_id
  ON public.company
  USING btree
  (id);

