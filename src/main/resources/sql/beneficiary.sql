-- Table: public.beneficiary

-- DROP TABLE public.beneficiary;

CREATE TABLE public.beneficiary
(
  id bigint NOT NULL DEFAULT nextval('beneficiary_id_seq'::regclass),
  "firstName" text NOT NULL,
  "lastName" text NOT NULL,
  CONSTRAINT pk_beneficiary_id PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.beneficiary
  OWNER TO postgres;

-- Index: public.ix_beneficiary_id

-- DROP INDEX public.ix_beneficiary_id;

CREATE INDEX ix_beneficiary_id
  ON public.beneficiary
  USING btree
  (id);

