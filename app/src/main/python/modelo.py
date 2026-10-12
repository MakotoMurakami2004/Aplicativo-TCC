import os
import re
import sys
import joblib
import pandas as pd
import numpy


# ==========================================
# COMPATIBILIDADE NUMPY 2.X (COLAB) -> NUMPY 1.X (CHAQUOPY)
# ==========================================

if not hasattr(numpy, "_core"):
    import numpy.core
    sys.modules["numpy._core"] = numpy.core
    for name, mod in list(sys.modules.items()):
        if name.startswith("numpy.core"):
            sys.modules[name.replace("numpy.core", "numpy._core", 1)] = mod

    class NumPyCoreFinder:
        def find_module(self, fullname, path=None):
            if fullname.startswith("numpy._core"):
                return self
            return None

        def load_module(self, fullname):
            real_name = fullname.replace("numpy._core", "numpy.core", 1)
            __import__(real_name)
            mod = sys.modules[real_name]
            sys.modules[fullname] = mod
            return mod

    sys.meta_path.insert(0, NumPyCoreFinder())


# ==========================================
# CARREGAMENTO DO MODELO
# ==========================================

CAMINHO_MODELO = os.path.join(
    os.path.dirname(__file__),
    "modelo.pkl"
)

with open(CAMINHO_MODELO, "rb") as f:
    modelo = joblib.load(f)


# ==========================================
# EXTRAÇÃO DAS CARACTERÍSTICAS
# ==========================================

def extract_features(url):
    """Analisa o texto da URL e extrai as mesmas
    características utilizadas no treinamento."""

    url = str(url).strip().lower()

    # Adiciona https:// somente para padronização
    if not url.startswith(("http://", "https://")):
        url = "https://" + url

    return {

        # Características gerais da URL
        "tamanho_url": len(url),

        "qtd_pontos": url.count("."),

        "qtd_hifens": url.count("-"),

        "qtd_arroba": url.count("@"),

        "qtd_interrogacao": url.count("?"),

        "qtd_igual": url.count("="),


        # Conta somente as barras do caminho.
        # Ignora https://, http:// e a barra final.
        "qtd_barras": url.replace(
            "https://", ""
        ).replace(
            "http://", ""
        ).strip("/").count("/"),


        "qtd_digitos": sum(
            c.isdigit() for c in url
        ),


        # HTTPS
        "tem_https": int(
            url.startswith("https://")
        ),


        # Verifica se existe um endereço IP na URL
        "tem_ip": int(
            re.search(
                r"\b(?:[0-9]{1,3}\.){3}[0-9]{1,3}\b",
                url
            ) is not None
        ),


        # Quantidade de pontos/subdomínios
        "qtd_subdominios": url.count("."),


        # ==========================================
        # INDICADORES DE DOMÍNIOS INSTITUCIONAIS
        # ==========================================

        "tem_gov": int(
            "gov" in url
        ),

        "tem_edu": int(
            "edu" in url
        ),

        "tem_org": int(
            "org" in url
        ),

        "tem_br": int(
            ".br" in url
        ),

        "tem_fgv": int(
            "fgv" in url
        ),

        "tem_ufrn": int(
            "ufrn" in url
        ),

        "tem_unesp": int(
            "unesp" in url
        ),

        "tem_ufsc": int(
            "ufsc" in url
        ),

        "tem_ufrj": int(
            "ufrj" in url
        ),


        # ==========================================
        # PALAVRAS COMUNS EM PHISHING
        # ==========================================

        "tem_login": int(
            "login" in url
        ),

        "tem_verify": int(
            "verify" in url
        ),

        "tem_secure": int(
            "secure" in url
        ),

        "tem_account": int(
            "account" in url
        ),

        "tem_update": int(
            "update" in url
        ),

        "tem_bank": int(
            "bank" in url
        ),

        "tem_paypal": int(
            "paypal" in url
        ),

        "tem_free": int(
            "free" in url
        ),
    }

# ==========================================
# VERIFICAÇÃO DA URL
# ==========================================

def verificar_url(url):

    # Extrai as características
    dados = pd.DataFrame([
        extract_features(url)
    ])

    # Ajusta as colunas para coincidir com as características usadas no treinamento do modelo
    if hasattr(modelo, "feature_names_in_"):
        colunas_esperadas = modelo.feature_names_in_
        for col in colunas_esperadas:
            if col not in dados.columns:
                dados[col] = 0
        dados = dados[colunas_esperadas]

    # Faz a previsão
    resultado = modelo.predict(dados)[0]

    # Obtém as probabilidades
    probabilidade = modelo.predict_proba(dados)[0]

    return {
        "resultado": int(resultado),

        "seguro": float(
            probabilidade[0]
        ),

        "phishing": float(
            probabilidade[1]
        ),
    }