package com.hp.trackzonec.model;

import java.util.List;

public class IPCFilterModel {
    /**
     * status : Success
     * Section_Details : [{"section_id":"1","section_no":"SECTION 375","title":"Rape","description":"Section 375, IPC defines rape. In simple terms, the offence of rape is the ravishment of a woman, without her consent, by force, fraud or fear. In other words, it is the carnal knowledge (penetration of any of the slightest degree of the male organ of reproduction) of any woman by force against her will. It is an obnoxious act of highest degree which violates the right to privacy and sanctity of a female. Apart from being a dehumanizing and perverted act, it is also an unlawful interference in the personal life of a woman which is an intense blow on the honor, dignity, reputation and self-esteem of a woman. This outrageous crime not only causes physical injury to the victim but also humiliates, degrades and leaves a scar on the most precious jewel of a woman i. e. her character and dignity.\n\nEssential Ingredients of Rape\n\nSection 375 has the following two essential ingredient-\n\nActus Reus: There must be sexual intercourse, as understood in terms of the provisions of Section 375 (a) to "},{"section_id":"2","section_no":"SECTION 376","title":"Punishment for Rape","description":"Whoever, except in the cases provided for in sub-section (2), commits rape, shall be punished with rigorous imprisonment of either description for a term which shall not be less than ten2 years, but which may extend to imprisonment for life, and shall also be liable to fine.\nWhoever\u2014\nbeing a police officer, commits rape,\nwithin the limits of the police station to which such police officer is appointed; or\nin the premises of any station house; or\non a woman in such police officer\u2019s custody or in the custody of a police officer subordinate to such police officer; or\nbeing a public servant, commits rape on a woman in such public servant\u2019s custody or in the custody of a public servant subordinate to such public servant; or\nbeing a member of the armed forces deployed in an area by the Central or a State Government commits rape in such area; or\nbeing on the management or on the staff of a jail, remand home or other place of custody established by or under any law for the time being in "},{"section_id":"3","section_no":"SECTION 376D","title":"Gang Rape","description":"Where a woman is raped by one or more persons constituting a group or acting in furtherance of a common intention, each of those persons shall be deemed to have committed the offence of rape and shall be punished with rigorous imprisonment for a term which shall not be less than twenty years, but which may extend to life which shall mean imprisonment for the remainder of that person\u2019s natural life, and with fine;\n\nProvided that such fine shall be just and reasonable to meet the medical expenses and rehabilitation of the victim;\n\nProvided further that any fine imposed under this section shall be paid to the victim.\n\n1 Criminal Law (Amendment) Act, 2013"}]
     */

    private String status;
    private List<SectionDetailsBean> Section_Details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SectionDetailsBean> getSection_Details() {
        return Section_Details;
    }

    public void setSection_Details(List<SectionDetailsBean> Section_Details) {
        this.Section_Details = Section_Details;
    }

    public static class SectionDetailsBean {
        /**
         * section_id : 1
         * section_no : SECTION 375
         * title : Rape
         * description : Section 375, IPC defines rape. In simple terms, the offence of rape is the ravishment of a woman, without her consent, by force, fraud or fear. In other words, it is the carnal knowledge (penetration of any of the slightest degree of the male organ of reproduction) of any woman by force against her will. It is an obnoxious act of highest degree which violates the right to privacy and sanctity of a female. Apart from being a dehumanizing and perverted act, it is also an unlawful interference in the personal life of a woman which is an intense blow on the honor, dignity, reputation and self-esteem of a woman. This outrageous crime not only causes physical injury to the victim but also humiliates, degrades and leaves a scar on the most precious jewel of a woman i. e. her character and dignity.

         Essential Ingredients of Rape

         Section 375 has the following two essential ingredient-

         Actus Reus: There must be sexual intercourse, as understood in terms of the provisions of Section 375 (a) to
         */

        private String section_id;
        private String section_no;
        private String title;
        private String description;

        public String getSection_id() {
            return section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public String getSection_no() {
            return section_no;
        }

        public void setSection_no(String section_no) {
            this.section_no = section_no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
